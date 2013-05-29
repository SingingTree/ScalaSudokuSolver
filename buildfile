# Buildr.settings.build['scala.version'] = '2.10.0' # Need to tell which version we want before the require (2.10.X seems broken as of 2013 - 05 - 23)
ENV['JAVA_HOME'] = 'C:\Program Files (x86)\Java\jdk1.7.0_21' #need a 32 bit version of Java to go with 32 bit version of ruby (Otherwise get a can't start VM error message)
repositories.remote << 'http://repo1.maven.org/maven2' #need this for jmock (default repos don't have it seems)

require 'buildr/scala'

Buildr.settings.build['scalac.incremental'] = true

define 'SudokuSolver'