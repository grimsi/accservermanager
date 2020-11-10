package grimsi.accservermanager.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.stream.Stream;

@Service
public class FileSystemService {

    private final Logger log = LoggerFactory.getLogger(FileSystemService.class);
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

        File applicationFolder = new File(getRootFolderPath(false));

        if (!applicationFolder.exists()) {
            log.error("The application folder could not be found.\nAre you sure that you set the correct path in the 'application.properties' file?");
        }

        File instanceRootFolder = new File(getRootFolderPath(false) + File.separator + INSTANCES_FOLDER);

        /* create 'instances' folder */
        if (!instanceRootFolder.exists() && instanceRootFolder.mkdirs()) {
            log.debug("Created instace root folder.");
        }
    }

    public List<String> getInstalledServerVersions() {
        File serverRootFolder = new File(getRootFolderPath(false) + File.separator + SERVERS_FOLDER);

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

    public String getInstanceFolderPath(InstanceDto instance, boolean ignoreContainerized) {
        return getRootFolderPath(ignoreContainerized) + File.separator + INSTANCES_FOLDER + File.separator + instance.getId();
    }

    @SuppressWarnings("Duplicates")
    public void createInstanceFolder(InstanceDto instance) {
        File serverRootFolder = new File(getRootFolderPath(false) + File.separator + SERVERS_FOLDER);
        File instanceFolder = new File(getInstanceFolderPath(instance, false));

        if (instanceFolder.exists() && instanceFolder.delete()) {
            log.debug("Deleted existing folder for instance: '" + instance.getId() + "'.");
        }

        try {
            log.debug("Creating folder '" + instanceFolder.getAbsolutePath() + "'.");

            if (instanceFolder.mkdirs()) {
                log.debug("Created folder '" + instanceFolder.getAbsolutePath() + "'.");
            }

            copyServerExecutable(serverRootFolder, instanceFolder, instance.getVersion());
            createLogFolder(instanceFolder);
            createCfgFolder(instanceFolder, instance);

        } catch (IOException e) {
            throw new CouldNotCreateFolderException(instanceFolder.toPath());
        }
    }

    public void deleteInstanceFolder(InstanceDto instance) {
        File folderToDelete = new File(getInstanceFolderPath(instance, false));

        log.debug("Deleting folder '" + folderToDelete.getAbsolutePath() + "'.");

        deleteFolder(folderToDelete);
    }

    @SuppressWarnings("Duplicates")
    public void updateInstanceFolder(InstanceDto instance) {
        File serverRootFolder = new File(getRootFolderPath(false) + File.separator + SERVERS_FOLDER);
        File instanceFolder = new File(getInstanceFolderPath(instance, false));
        File configFolder = new File(instanceFolder.getAbsolutePath() + File.separator + CFG_FOLDER);
        File serverExecutable = new File(getInstanceFolderPath(instance, false) + config.getServerExecutableName());

        deleteFolder(configFolder);

        if (serverExecutable.delete()) {
            log.debug("Deleted server executable for instance '" + instance.getId() + "'.");
        }

        try {
            copyServerExecutable(serverRootFolder, instanceFolder, instance.getVersion());
            createCfgFolder(instanceFolder, instance);
        } catch (IOException e) {
            throw new CouldNotUpdateFolderException(instanceFolder.toPath());
        }
    }

    public boolean instanceHasValidFolder(InstanceDto instance) {
        String instanceRootFolderPath = getInstanceFolderPath(instance, false);
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

    private String getRootFolderPath(boolean ignoreContainerize) {
        if (!ignoreContainerize && config.isContainerized()) {
            return config.getFolderPathContainerized();
        } else {
            return config.getFolderPath();
        }
    }

    private void deleteFolder(File folder) {
        try (Stream<Path> fileStream = Files.walk(folder.toPath())) {
            fileStream.map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach((file) -> {
                        if (file.delete()) {
                            log.debug("Deleted file '" + file.getAbsolutePath() + "'.");
                        }
                    });
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

        if (logFolder.mkdirs()) {
            log.debug("Created log folder in '" + instanceFolder + "'.");
        }

        File logErrorFolder = new File(logFolder.getAbsolutePath() + File.separator + LOG_ERROR_FOLDER);

        if (logErrorFolder.mkdirs()) {
            log.debug("Created log-error folder in '" + instanceFolder + "'.");
        }
    }

    private void createCfgFolder(File instanceFolder, InstanceDto instance) throws IOException {
        File cfgFolder = new File(instanceFolder.getAbsolutePath() + File.separator + CFG_FOLDER);

        if (cfgFolder.mkdirs()) {
            log.debug("Created config folder in '" + instanceFolder + "'.");
        }

        File configurationJson = new File(cfgFolder.getAbsolutePath() + File.separator + CONFIGURATION_JSON);
        if (configurationJson.createNewFile()) {
            writeFile(configurationJson, gson.toJson(instance.getConfiguration()));
            log.debug("Created configuration.json in '" + configurationJson.getAbsolutePath() + "'.");
        }

        File settingsJson = new File(cfgFolder.getAbsolutePath() + File.separator + SETTINGS_JSON);
        if (settingsJson.createNewFile()) {
            writeFile(settingsJson, gson.toJson(instance.getSettings()));
            log.debug("Created settings.json in '" + settingsJson.getAbsolutePath() + "'.");
        }

        File eventJson = new File(cfgFolder.getAbsolutePath() + File.separator + EVENT_JSON);
        if (eventJson.createNewFile()) {
            writeFile(eventJson, gson.toJson(instance.getEvent()));
            log.debug("Created event.json in '" + eventJson.getAbsolutePath() + "'.");
        }
    }

    private void writeFile(File file, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
            fileWriter.flush();
        }
    }

}
