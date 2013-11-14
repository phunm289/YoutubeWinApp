; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Desktube"
#define MyAppVersion "1.0"
#define MyAppPublisher "Phunm"
#define MyAppURL "http://www.facebook.com/phunm289"
#define MyAppExeName "Desktube.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{43747B08-04C2-4015-9184-5A7D20E253DD}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputDir=C:\Users\Wise-SW\Desktop
OutputBaseFilename=DesktubeSetup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\Wise-SW\Desktop\Desktube.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "D:\Server\PhuNM\lib\*"; DestDir: "{app}\lib\"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "D:\Server\PhuNM\Youtube\libvlc.dll"; DestDir: "{app}"; Flags: ignoreversion
Source: "D:\Server\PhuNM\Youtube\libvlccore.dll"; DestDir: "{app}"; Flags: ignoreversion
Source: "D:\Server\PhuNM\Youtube\plugins\*"; DestDir: "{app}\plugins\"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent
