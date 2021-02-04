PUT CHANGES IN HERE

~~Febuary 3 10:30pm - Mathias~~
    Fixed the SwerveDriveTrain Drive Method
    Finalized AlignSwerveModulesMathias (Some changes pending)
    Made a setMotorSpeed and setModuleSpeed
    Added Arrays for each motors (rotation and momentum) and sensors
    Updated Constants including motorIds and encoder resolution, if you are plaining on changing motorid constants please tell me
    added gets for the arrays
~~Febuary 4 9:00am - Mathias~~
    Made preperations to add math to the driver inputs
    Revmove the joystick and xbox constants and swapped anything that was using that onto the Enum one
    added gets for JOYSTICK and XBOX enum
    Changed encoder resolution to 4096 in constants
    Added encoder reset methods for singular and all
    fixed an issue where the first item in the arrays would not be accesable
    made gryo reset on start in SwerveDriveTrain
    Added System.out everywhere to help with debugging