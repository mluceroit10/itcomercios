#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <shellapi.h>
void main(int ARGC,char *ARGV[])
{
   ShellExecute(NULL,"open","cmd.exe"," /C \"C:/Program Files (x86)/Java/jre6/bin/java.exe\" -classpath cliente.jar;lib/jdo.jar;lib/jdo2.jar;lib/jdori.jar;lib/jdori-enhancer.jar;lib/log4j-1.2.8.jar;lib/tjdo.jar;lib/xml-apis.jar;lib/mm.mysql-2.0.14-bin.jar;lib/mysql-connector-java-3.0.11-stable-bin.jar;lib/commons-beanutils.jar;lib/commons-collections.jar;lib/commons-digester.jar;lib/commons-logging.jar;lib/itext-0.81.jar;lib/itext-xml-0.93.jar;lib/jasperreports-0.4.6.jar;lib/jcommon-0.6.4.jar;lib/jfreechart-0.9.2.jar;lib/tools.jar;lib/xercesImpl.jar;lib/xercesSamples.jar;lib/xml-apis.jar;lib/xmlParserAPIs.jar;lib/jcalendar.jar;lib/looks-1.2.2.jar cliente.Main",NULL,SW_HIDE);
}