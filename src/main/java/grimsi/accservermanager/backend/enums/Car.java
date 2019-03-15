package grimsi.accservermanager.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Car {
    CAR1("Car 1", Arrays.asList("Skin 1", "Skin 2")),
    CAR2("Car 2", Arrays.asList("Skin 3", "Skin 4"));

    private int name;
    private List<String> skins;

    private Car(String name, List<String> skins) {

    }
}
