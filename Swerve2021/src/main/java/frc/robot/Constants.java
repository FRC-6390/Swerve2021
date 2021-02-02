package frc.robot;

public interface Constants {

    int JOYSTICK_CONTROLLER = 0;
    int XBOX_CONTROLLER = 1;

    /*-------------------------MOTORS--------------------*/
    // DRIVE TRAIN MOTORS
    enum MOTORID {
        FRONT_LEFT_ROTATION(1),
        FRONT_LEFT_MOMENTUM(2),
        FRONT_RIGHT_ROTATION(3),
        FRONT_RIGHT_MOMENTUM(4),
        BACK_LEFT_ROTATION(5),
        BACK_LEFT_MOMENTUM(6),
        BACK_RIGHT_ROTATION(7),
        BACK_RIGHT_MOMENTUM(8);

        private int id;
        private MOTORID(int id){
            this.id = id;
        }

        public int GetID(){
            return id;
        }
    }
    enum SENSORS {
        FRONT_LEFT_ENCODER(1),
        FRONT_RIGHT_ENCODER(2),
        BACK_LEFT_ENCODER(3),
        BACK_RIGHT_ENCODER(4);

        private int id;
        private SENSORS(int id){
            this.id = id;
        }

        public int GetID(){
            return id;
        }
    }


    enum JOYSTICK {
        JOYSTICK_BUTTON_1(1),
        JOYSTICK_BUTTON_2(2),
        JOYSTICK_BUTTON_3(3),
        JOYSTICK_BUTTON_4(4),
        JOYSTICK_BUTTON_5(5),
        JOYSTICK_BUTTON_6(6),
        JOYSTICK_BUTTON_7(7),
        JOYSTICK_BUTTON_8(8),
        JOYSTICK_BUTTON_9(9),
        JOYSTICK_BUTTON_10(10),
        JOYSTICK_BUTTON_11(11),
        JOYSTICK_BUTTON_12(12),
        JOYSTICK_AXIS_Y(1),
        JOYSTICK_AXIS_Z(2),
        JOYSTICK_AXIS_THROTTLE(3),
        JOYSTICK_PORT_ID(0);

        private int id;
        private JOYSTICK(int id){
            this.id = id;
        }

        public int GetButton(){
            return id;
        }
    }
    
    enum XBOX {
        XBOX_A(1),
        XBOX_B(2),
        XBOX_X(3),
        XBOX_Y(4),
        XBOX_BUMBER_LEFT(5),
        XBOX_BUMBER_RIGHT(6),
        XBOX_BACK(7),
        XBOX_START(8),
        XBOX_LEFT_JOYSTICK_IN(9),
        XBOX_RIGHT_JOYSTICK_IN(10),
        XBOX_LEFT_AXIS_Y(1),
        XBOX_LEFT_AXIS_X(0),
        XBOX_RIGHT_TRIGGER(2),
        XBOX_LEFT_TRIGGER(3),
        XBOX_RIGHT_AXIS_Y(5),
        XBOX_RIGHT_AXIS_X(4),
        XBOX_PORT_ID(1);

        private int id;
        private XBOX(int id){
            this.id = id;
        }

        public int GetButton(){
            return id;
        }

        public int GetAxis(){
            return id;
        }

        double Threshold = 0.5;
        public void setThreshold(double Threshold){
            this.Threshold = Threshold;
        }

        public double getThreshold(){
            return Threshold;
        }

    }
  
    int PDP_DEVICE_ID = 0;
    double SWERVE_LOCATION_FROM_CENTER = 0.3302;

    
    
}
