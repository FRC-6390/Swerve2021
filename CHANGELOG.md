PUT CHANGES IN HERE

~~February 3 - Mathias
    Fixed the SwerveDriveTrain Drive Method
    Finalized AlignSwerveModulesMathias (Some changes pending)
    Made a setMotorSpeed and setModuleSpeed
    Added Arrays for each motors (rotation and momentum) and sensors
    Updated Constants including motorIds and encoder resolution, if you are plaining on changing motorid constants please tell me
    added gets for the arrays

~~February 4 - Mathias
    Made preperations to add math to the driver inputs
    Remove the joystick and xbox constants and swapped anything that was using that onto the Enum one
    added gets for JOYSTICK and XBOX enum
    Changed encoder resolution to 4096 in constants
    Added encoder reset methods for singular and all
    fixed an issue where the first item in the arrays would not be accesable
    made gryo reset on start in SwerveDriveTrain
    Added System.out everywhere to help with debugging
    Updated Diagram and added it to SwerveDriveTrain
    Minor var name changes
    Moved the cheese log and added a readme

~~February 5 - Mathias
    Moved cheese log back and got rid of the first folder in the repo
    Optimized SwerveModule a little bit
    
~~February 6 - Mohammad
    added methods at the bottom of SwerveModule.java Subystem responsible for converting raw units / meters / velocity we ca use these methods instead of .setDistancePerPulse() as it is not available for TalonFX. Cleaned some code. Please note that I am not completly done but I will push so that anyone can have the latest update. also its 2:30 so YAAAA!

~~February 8 - Mathias
    More swerve encoder changes, this way of doing it may actually work
    added a ROBOT enum for all things related to the robot like dimensions
    moved some swerve constants into the SWERVE enum
    changed anything that was affect by the previous constants changes
    Diagram is thicc no more
    Started work on a file manager

~~February 10 - Mathias
    Moved some files arround and renamed the SwerveDriveTrain var in robot to driveTrain
    Shelved DriveTrain, turned it into a text file
    Fixed anything that was affect by the changes above
    more changes to FileManger

~~February 11 - Mohammad
    Fixed errors and added comments everywhere so anyone can undersatnd
    Whenever writing code PLS WRITE COMMENTS to avoid later confusion
    Added new Dashboard still in progress got a timer, Camera, and Gyro
    so far. Wont be able to test them until we have a working robot.
    renamed AlignSwerveDriveModules and got rid of the other one
                                    ^^^ 
    Mathias: You gave me small panic attack when i couldnt find my swerve drive alignment code
    Mohammad: LOL

~~February 18 - Mathias
    Fixed issue with SwerveModule.java being messed up, re-added everything that was missing
    
~~February 18 - Mohammad
    Fiexed error in file Manager 

~~February 22 - Mathias
    Major FileManager changes, should be better overall. None of it tested but passed my mental test so I believe its good to go

~~February 23 - Mathias
    Alot more FileManager changes, FileManager is now RIOLog and all code relying on FileManager has been chaneged
    RIOWritter has been given support for more data types
    Added lots of method descriptions
    Added log levels and bypass log level
    Changed alot of RIOLog stuff, its late but none of the changes were major just made the code better
    Changed all the drive train arrays to make it stop yelling at me and changed anything that was affect

~~February 23 - Mohammad
    Added feature to connect to robots network tables for the custom dashboard to allow us to grab data and display it. This is also
    not tested, but should be able to connect to the robot at the moment.
    added vission folder and for all vission related stuff, as of now I have just created a thread for a camera server it is still in progress so nobody touch it for now as I am  not yet finished implementing it.

~~February 24 - Mathias
    Added some novelty things 
    fixed an issue where to error output text wouldnt output what class it came from
    Compressed the 3 files for RIOLog RIOLevel RIOWritter all into RIOLog.
    nothing should have chnaged and everything should work the same.
    PART2
    Changed RIOLog to RioLog, fixed almost all the issues, the only thing that is not working is ERRORS not printing to the console (prints to the file) and bypassing not working either

~~February 25 - Mohammad
    cleaned some code and added sub folders so that files can be placed in the folder relative to its day
    this will have to be tested on the robot to confirm it works, if you nottice I skipped a step let me please let me know

~~February 26 - Mohammad
    we cant spell lol... its **February** not **Febuary** :)

~~ March 4, 2021 - Mohammad 
    created testing branch and setted it up with neccesary files and folder to get it started. to switch between main and testing branch you will have to click the three dotes ... in your source control and press **Check out to** and then you can switch between them.
    I also added motor safety impleentation for all drive motors and rotation motors also added safety to their motor controllers. The numbers are under the max that falcons can go so that we can make sure they work without going to the max amps.
