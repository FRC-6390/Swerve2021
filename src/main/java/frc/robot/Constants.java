package frc.robot;

public interface Constants {

    // DRIVE TRAIN MOTORS
    enum MOTORID {
        FRONT_LEFT_ROTATION(0,"Front left rotation"),
        FRONT_LEFT_MOMENTUM(4,"Front left momentum"),
        FRONT_RIGHT_ROTATION(1,"Front right rotation"),
        FRONT_RIGHT_MOMENTUM(5,"front right momentum"),
        BACK_LEFT_ROTATION(2,"Back left rotation"),
        BACK_LEFT_MOMENTUM(6,"Back left momentum"),
        BACK_RIGHT_ROTATION(3,"Back right rotation"),
        BACK_RIGHT_MOMENTUM(7,"Back right momentum");

        private int id;
        private String name;
        private MOTORID(int id,String name){
            this.id = id;
            this.name = name;
        }

        public int GetID(){
            return id;
        }

        public String GetName(){
            return name;
        }
    }

    //Swerve Module Values
    enum SWERVE {
        FRONT_LEFT_MODULE(0),
        FRONT_RIGHT_MODULE(1),
        BACK_LEFT_MODULE(2),
        BACK_RIGHT_MODULE(3),
        LOCATION_FROM_CENTER(0.3302),
        GEAR_RATIO(8.16);

        private int id;
        private double doubles;
        private SWERVE(int id){
            this.id = id;
        }

        private SWERVE(double id){
            this.doubles = id;
        }

        public int GetID(){
            return id;
        }

        public int GetEncoder(){
            return id; //this is  ment to do the same as the above bc the encoders have the same ids as the modules
        }

        public double get(){
            return doubles;
        }
    }

    //Might Have to Seperate Later
    //Limit Switches
    //Encoderss
    enum SENSORS {
        FRONT_LEFT_ENCODER(0),
        FRONT_RIGHT_ENCODER(1),
        BACK_LEFT_ENCODER(2),
        BACK_RIGHT_ENCODER(3),
        FRONT_LEFT_LIMIT(0), 
        FRONT_RIGHT_LIMIT(1), 
        BACK_RIGHT_LIMIT(2),
        BACK_LEFT_LIMIT(3),
        EXTERNAL_ENCODER_RESOLUTION(4096),// maybe 1024
        INTERNAL_ENCODER_RESOLUTION(2048); 


        private int id;
        private double resolution;
        private SENSORS(int id){
            this.id = id;
        }

        private SENSORS(double resolution){
            this.resolution = resolution;
        }

        public int GetID(){
            return id;
        }

        public double GetResolution(){
            return resolution;
        }
    }

    //Logitech Controler Buttons and Axises
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
        JOYSTICK_PORT_ID(1);

        private int id;
        private JOYSTICK(int id){
            this.id = id;
        }

        public int GetButton(){
            return id;
        }

        public int Get(){
            return id;
        }
    }
    
    //Xbox Controller Buttons and Axises
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
        XBOX_PORT_ID(0);

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

        public int Get(){
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

    //Robot Details
    enum ROBOT{
        WHEEL_RADIUS(0.0508),
        MAX_SPEED(3.0),
        MAX_ANGULAR_SPEED(3.145),
        MAX_ANGULAR_ACCELERATION(6.29);

        private double id;
        private ROBOT(double id){
            this.id = id;
        }

        public double get(){
            return id;
        }
       
    }

    enum FILES{
        ROBORIO_OUTPUT("/home/lvuser/output/"),
        USB_OUTPUT("u/output/"),
        HEPHAESTUS("      _    _            _                     _                 __ ____   ___   ___  \n     | |  | |          | |                   | |               / /|___ \\ / _ \\ / _ \\ \n     | |__| | ___ _ __ | |__   __ _  ___  ___| |_ _   _ ___   / /_  __) | (_) | | | |\n     |  __  |/ _ \\ '_ \\| '_ \\ / _` |/ _ \\/ __| __| | | / __| | '_ \\|__ < \\__, | | | |\n     | |  | |  __/ |_) | | | | (_| |  __/\\__ \\ |_| |_| \\__ \\ | (_) |__) |  / /| |_| |\n     |_|  |_|\\___| .__/|_| |_|\\__,_|\\___||___/\\__|\\__,_|___/  \\___/____/  /_/  \\___/ \n                 | |                                                                 \n                 |_|            \n");


        private String m_String;
        private FILES(String string){
            m_String = string;
        }

        public String get(){
            return m_String;
        }

        public String getFolder(){
            return m_String + "/";
        }
    }

    /**_____________________NON ENUM VALUES_____________________ */

    //Power Distribution Panel
    int PDP_DEVICE_ID = 0;    

    
    
}
