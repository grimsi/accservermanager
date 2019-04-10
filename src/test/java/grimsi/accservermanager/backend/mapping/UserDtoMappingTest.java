package grimsi.accservermanager.backend.mapping;

import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.entity.User;
import grimsi.accservermanager.backend.interfaces.MappingUnitTest;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class UserDtoMappingTest implements MappingUnitTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void convertEntityToDto() {
        User user = new User();
        user.setId("123");
        user.setUsername("testUser");
        user.setPassword("testPassword");

        UserDto userDto = modelMapper.map(user, UserDto.class);

        checkMapping(user, userDto);
    }

    @Test
    public void convertDtoToEntity() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");

        User user = modelMapper.map(userDto, User.class);

        checkMapping(user, userDto);
    }

    private void checkMapping(User user, UserDto userDto) {
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getPassword(), userDto.getPassword());
    }
}
