package grimsi.accservermanager.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import grimsi.accservermanager.backend.ACCServerManager;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.exception.CouldNotCreateFolderException;
import grimsi.accservermanager.backend.exception.CouldNotDeleteFolderException;
import grimsi.accservermanager.backend.exception.CouldNotUpdateFolderException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSystemService {

    private final Logger log = LoggerFactory.getLogger(ACCServerManager.class);
    private final String SERVERS_FOLDER = "servers";
    private final String INSTANCES_FOLDER = "instances";
    private final String LOG_FOLDER = "log";
    private final String LOG_ERROR_FOLDER = "error";
    private final String CFG_FOLDER = "cfg";
    private final String CONFIGURATION_JSON = "configuration.json";
    private final String EVENT_JSON = "event.json";
    private final String SETTINGS_JSON = "settings.json";
    @Autowired
    ApplicationConfiguration config;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void initFileSystem() {
        File instanceRootFolder = new File(config.getFolderPath() + File.separator + INSTANCES_FOLDER);
        /* create 'instances' folder */
        if (!instanceRootFolder.exists()) {
            instanceRootFolder.mkdirs();
        }
    }

    public List<String> getInstalledServerVersions() {
        File serverRootFolder = new File(config.getFolderPath() + File.separator + SERVERS_FOLDER);

        if (!serverRootFolder.exists()) {
            log.error(serverRootFolder.getAbsolutePath() + " could not be found.");
            return Collections.emptyList();
        }

        File[] subDirectories = serverRootFolder.listFiles(File::isDirectory);
        if (subDirectories != null) {
            return Arrays.stream(subDirectories).map(File::getName).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public String getInstanceFolderPath(InstanceDto instance) {
        return config.getFolderPath() + File.separator + INSTANCES_FOLDER + File.separator + instance.getId();
    }

    @SuppressWarnings("Duplicates")
    public void createInstanceFolder(InstanceDto instance) {
        File serverRootFolder = new File(config.getFolderPath() + File.separator + SERVERS_FOLDER);
        File instanceFolder = new File(getInstanceFolderPath(instance));

        if (instanceFolder.exists()) {
            instanceFolder.delete();
        }

        try {
            log.info("Creating folder '" + instanceFolder.getAbsolutePath() + "'.");
            instanceFolder.mkdirs();

            copyServerExecutable(serverRootFolder, instanceFolder, instance.getVersion());
            createLogFolder(instanceFolder);
            createCfgFolder(instanceFolder, instance);

        } catch (IOException e) {
            throw new CouldNotCreateFolderException(instanceFolder.toPath());
        }
    }

    public void deleteInstanceFolder(InstanceDto instance) {
        File folderToDelete = new File(getInstanceFolderPath(instance));
        deleteFolder(folderToDelete);
    }

    @SuppressWarnings("Duplicates")
    public void updateInstanceFolder(InstanceDto instance) {
        File serverRootFolder = new File(config.getFolderPath() + File.separator + SERVERS_FOLDER);
        File instanceFolder = new File(getInstanceFolderPath(instance));
        File configFolder = new File(instanceFolder.getAbsolutePath() + File.separator + CFG_FOLDER);
        File serverExecutable = new File(getInstanceFolderPath(instance) + config.getServerExecutableName());

        deleteFolder(configFolder);

        serverExecutable.delete();

        try {
            copyServerExecutable(serverRootFolder, instanceFolder, instance.getVersion());
            createCfgFolder(instanceFolder, instance);
        } catch (IOException e) {
            throw new CouldNotUpdateFolderException(instanceFolder.toPath());
        }
    }

    public boolean instanceHasValidFolder(InstanceDto instanceDto) {
        String instanceRootFolderPath = getInstanceFolderPath(instanceDto);
        String instanceCfgFolderPath = instanceRootFolderPath + File.separator + CFG_FOLDER;
        String instanceLogFolderPath = instanceRootFolderPath + File.separator + LOG_FOLDER;

        List<File> filesToCheck = new ArrayList<>();

        filesToCheck.add(new File(instanceRootFolderPath));
        filesToCheck.add(new File(instanceCfgFolderPath));
        filesToCheck.add(new File(instanceCfgFolderPath + File.separator + SETTINGS_JSON));
        filesToCheck.add(new File(instanceCfgFolderPath + File.separator + EVENT_JSON));
        filesToCheck.add(new File(instanceCfgFolderPath + File.separator + CONFIGURATION_JSON));
        filesToCheck.add(new File(instanceLogFolderPath));
        filesToCheck.add(new File(instanceLogFolderPath + File.separator + LOG_ERROR_FOLDER));

        /* check if any of these files/folders do not exist */
        return filesToCheck.stream().allMatch(File::exists);
    }

    private void deleteFolder(File folder) {
        try {
            Files.walk(folder.toPath())
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new CouldNotDeleteFolderException(folder.toPath());
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

    private void createCfgFolder(File instanceFolder, InstanceDto instance) throws IOException {
        File cfgFolder = new File(instanceFolder.getAbsolutePath() + File.separator + CFG_FOLDER);
        cfgFolder.mkdirs();

        File configurationJson = new File(cfgFolder.getAbsolutePath() + File.separator + CONFIGURATION_JSON);
        configurationJson.createNewFile();
        writeFile(configurationJson, gson.toJson(instance.getConfiguration()));

        File settingsJson = new File(cfgFolder.getAbsolutePath() + File.separator + SETTINGS_JSON);
        settingsJson.createNewFile();
        writeFile(settingsJson, gson.toJson(instance.getSettings()));

        File eventJson = new File(cfgFolder.getAbsolutePath() + File.separator + EVENT_JSON);
        eventJson.createNewFile();
        writeFile(eventJson, gson.toJson(instance.getEvent()));
    }

    private void writeFile(File file, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

}
