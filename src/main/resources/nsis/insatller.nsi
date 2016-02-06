;
; $Id$
;
; Coreen Installer for Windows x86

!define LOCALE "es"
!define NAME "MG2"
!define INSTALL_DIR "MG2"
!define APPBASE "@root_url@"
!define OUTFILENAME "..\mg2-install.exe"

; comment this out to enable the code that automatically downloads
; the JVM from the web and installs it
!define BUNDLE_JVM true

!include "installer-common.nsi"
