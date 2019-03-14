package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.entity.Config;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.repository.ConfigRepository;
import grimsi.accservermanager.backend.repository.ConfigurationJsonRepository;
import grimsi.accservermanager.backend.repository.EventJsonRepository;
import grimsi.accservermanager.backend.repository.SettingsJsonRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {

    @Autowired
    JsonSchemaService jsonSchemaService;

    @Autowired
    ConfigRepository configRepository;

    @Autowired
    ConfigurationJsonRepository configurationJsonRepository;

    @Autowired
    EventJsonRepository eventJsonRepository;

    @Autowired
    SettingsJsonRepository settingsJsonRepository;

    @Autowired
    ModelMapper mapper;

    public List<ConfigDto> findAll() {
        List<Config> configs = configRepository.findAll();
        return mapper.map(configs, new TypeToken<List<ConfigDto>>() {
        }.getType());
    }

    public ConfigDto findById(String id) {
        Config config = configRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertToDto(config);
    }

    public void deleteById(String id) {
        findById(id);
        configRepository.deleteById(id);
    }

    public ConfigDto updateById(String id, ConfigDto configDto) {
        return create(configDto);
    }

    public ConfigDto create(ConfigDto configDto) {
        Config config = convertToEntity(configDto);

        config.configurationJson = configurationJsonRepository.save(config.configurationJson);
        config.settingsJson = settingsJsonRepository.save(config.settingsJson);
        config.eventJson = eventJsonRepository.save(config.eventJson);

        config = configRepository.save(config);
        return convertToDto(config);
    }

    public String getJsonSchema() {
        String schema = jsonSchemaService.getJsonSchema(ConfigDto.class);
        if (schema == null) {
            throw new NullPointerException("Error parsing JSON schema");
        }
        return schema;
    }

    private ConfigDto convertToDto(Config config) {
        return mapper.map(config, ConfigDto.class);
    }

    private Config convertToEntity(ConfigDto configDto) {
        return mapper.map(configDto, Config.class);
    }
}
