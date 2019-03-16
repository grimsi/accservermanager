package grimsi.accservermanager.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import grimsi.accservermanager.backend.ACCServerManager;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.exception.CouldNotCreateFolderException;
import grimsi.accservermanager.backend.exception.CouldNotDeleteFolderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSystemService {

    @Autowired
    ApplicationConfiguration config;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Logger log = LoggerFactory.getLogger(ACCServerManager.class);

    private final String SERVERS_FOLDER = "servers";
    private final String INSTANCES_FOLDER = "instances";
    private final String LOG_FOLDER = "log";
    private final String LOG_ERROR_FOLDER = "error";
    private final String CFG_FOLDER = "cfg";
    private final String CONFIGURATION_JSON = "configuration.json";
    private final String EVENT_JSON = "event.json";
    private final String SETTINGS_JSON = "settings.json";

    public List<String> getInstalledServerVersions(){
        File serverRootFolder = new File(config.getFolderPath() + File.separator + SERVERS_FOLDER);

        if(!serverRootFolder.exists()){
            log.error(serverRootFolder.getAbsolutePath() + " could not be found.");
            return Collections.emptyList();
        }

        File[] subDirectories = serverRootFolder.listFiles(File::isDirectory);
        if(subDirectories != null){
            return Arrays.stream(subDirectories).map(File::getName).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public void createInstanceFolder(InstanceDto instance){
        File instanceRootFolder = new File(config.getFolderPath() + File.separator + INSTANCES_FOLDER);
        File serverRootFolder = new File(config.getFolderPath() + File.separator + SERVERS_FOLDER);

        if(!instanceRootFolder.exists()){
            log.info("Creating folder '" + instanceRootFolder.getAbsolutePath() + "'.");
            instanceRootFolder.mkdirs();
        }

        File instanceFolder = new File(instanceRootFolder.getAbsolutePath() + File.separator + instance.getName());

        try {
            log.info("Creating folder '" + instanceFolder.getAbsolutePath() + "'.");
            instanceFolder.mkdirs();

            copyServerExecutable(serverRootFolder, instanceFolder, instance.getVersion());
            createLogFolder(instanceFolder);
            createCfgFolder(instanceFolder, instance.getConfig());

        } catch (IOException e){
            throw new CouldNotCreateFolderException(instanceFolder.toPath());
        }
    }

    public void deleteInstanceFolder(InstanceDto instance){
        File folderToDelete = new File(config.getFolderPath() + File.separator + INSTANCES_FOLDER + File.separator + instance.getName());
        try{
            Files.walk(folderToDelete.toPath())
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        } catch (IOException e){
            throw new CouldNotDeleteFolderException(folderToDelete.toPath());
        }
    }

    private void copyServerExecutable(File serverRootFolder, File instanceFolder, String serverVersion) throws IOException {
        File serverExecutable = new File(serverRootFolder.getAbsolutePath() + File.separator + serverVersion + File.separator + config.getServerExecutableName());

        Files.copy(serverExecutable.toPath(), Paths.get(instanceFolder.getAbsolutePath(), config.getServerExecutableName()), StandardCopyOption.REPLACE_EXISTING);
    }

    private void createLogFolder(File instanceFolder) {
        File logFolder = new File(instanceFolder.getAbsolutePath() + File.separator + LOG_FOLDER);
        logFolder.mkdirs();

        File logErrorFolder = new File(logFolder.getAbsolutePath() + File.separator + LOG_ERROR_FOLDER);
        logErrorFolder.mkdirs();
    }

    private void createCfgFolder(File instanceFolder, ConfigDto instanceConfig) throws IOException {
        File cfgFolder = new File(instanceFolder.getAbsolutePath() + File.separator + CFG_FOLDER);
        cfgFolder.mkdirs();

        File configurationJson = new File(cfgFolder.getAbsolutePath() + File.separator + CONFIGURATION_JSON);
        configurationJson.createNewFile();
        writeFile(configurationJson, gson.toJson(instanceConfig.getConfigurationJson()));

        File settingsJson = new File(cfgFolder.getAbsolutePath() + File.separator + SETTINGS_JSON);
        settingsJson.createNewFile();
        writeFile(settingsJson, gson.toJson(instanceConfig.getSettingsJson()));

        File eventJson = new File(cfgFolder.getAbsolutePath() + File.separator + EVENT_JSON);
        eventJson.createNewFile();
        writeFile(eventJson, gson.toJson(instanceConfig.getEventJson()));
    }

    private void writeFile(File file, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

}
