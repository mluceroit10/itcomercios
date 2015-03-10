;instalador Sistema Administrativo.nsi
; Este instalador toma todos los archivos los instala en el directorio c:\,
; además crea en el menu inicio los accesos directos al programa
;
;------------------------------

; Nombre del instalador
Name "SOFT_COMERCIO"

; El archivo de salida
OutFile "Instalador_Soft_itComercios.exe" 

; Directorio por defecto para la instalacion
InstallDir c:\

; requerir permisos para windows vista
RequestExecutionLevel admin

;--------------------------------

; paginas
Page components
Page instfiles

     
 

;--------------------------------
     
; El material para la instalación


Section "SA (requerido)"

SectionIn RO

; Toma el directorio de que se selecciono para la instalación.

SetOutPath $INSTDIR

; Pone los Archivos a instalar

File /r archivos_Soft_itComercios
File /r SoftItComercios

SectionEnd

     

; Sección opcional (Pudiera ser deshabilitada pero en este caso necesitamos crear todo.)

Section "Start Menu Shortcuts"

CreateDirectory "$SMPROGRAMS\Soft_itComercios"

CreateShortCut "$SMPROGRAMS\Soft_itComercios\Ejecutar_itComercios.lnk" "$INSTDIR\SoftItComercios\itComercios.lnk" "" "$INSTDIR\SoftItComercios\itComercios.lnk" 0

CreateShortCut "$DESKTOP\Ejecutar_itComercios.lnk" "$INSTDIR\SoftItComercios\itComercios.lnk" "" "$INSTDIR\SoftItComercios\itComercios.lnk" 0       

CreateShortCut "$SMPROGRAMS\Soft_itComercios\Manual_De_Usuario_itComercios.lnk" "$INSTDIR\SoftItComercios\MANUAL DE USUARIO.pdf" "" "$INSTDIR\SoftItComercios\MANUAL DE USUARIO.pdf" 0  

SectionEnd

