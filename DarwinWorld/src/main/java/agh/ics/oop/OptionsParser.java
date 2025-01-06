package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {

    public static List<MoveDirection> parse(String[] args) {

        List<MoveDirection> directions = new ArrayList<>();
        MoveDirection tmp;

        for (String arg : args) {
            try{
                tmp = switch(arg){
                    case "f" -> MoveDirection.FORWARD;
                    case "b" -> MoveDirection.BACKWARD;
                    case "l" -> MoveDirection.LEFT;
                    case "r" -> MoveDirection.RIGHT;
                    default -> throw new IllegalArgumentException(arg + " is not legal move specification");
                };
                directions.add(tmp);
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        return directions;

    }
}
