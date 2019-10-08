@ECHO OFF

SET BOTS=C:\BOTS

::###########################

:: Store Old Path
SET OLDPATH=%PATH%;

:: Bin (clean PATH)
SET PATH=%BOTS%\bin;

:: Code
SET PATH=%BOTS%\code;%BOTS%\code\bin;%PATH%

:: Composer
SET PATH=%BOTS%\composer;%PATH%

:: Composer Applications
SET PATH=%appdata%\Composer\vendor\bin;%PATH%

:: Git
SET PATH=%BOTS%\git;%BOTS%\git\bin;%PATH%

:: MySQL
SET PATH=%BOTS%\mysql\bin;%PATH%

:: PHP
SET PATH=%BOTS%\php;%PATH%

:: Restore Old Path
SET PATH=%PATH%;%OLDPATH%

::###########################

cmd

::###########################
