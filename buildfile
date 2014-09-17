# Buildr.settings.build['scala.version'] = '2.10.0' # Need to tell which version we want before the require (2.10.X seems broken as of 2013 - 05 - 23)

# need a 32 bit version of Java to go with 32 bit version of ruby (Otherwise get a can't start VM error message)
ENV['JAVA_HOME'] = 'C:\Program Files (x86)\Java\jdk1.7.0_21'

#need this for jmock (default repos don't have it seems)
repositories.remote << 'http://repo1.maven.org/maven2'

require 'buildr/scala'
require 'fileutils'

# uberjar is specific to our project, but we don't want to have to qualify it (i.e. in the shell "uberjar" is nicer than "SudokuSolver:uberjar")
Project.local_task :uberjar

Buildr.settings.build['scalac.incremental'] = true

assembly_dir = 'target/assembly'
main_class = 'sudoku.SudokuGUI'

define 'SudokuSolver' do
	project.version = '0.2'

	compile.with('org.scala-lang:scala-swing:jar:' + Scala.version)
	compile.using :warnings => true, :deprecation => true, :other=>'-unchecked'
	
	run.using :main => main_class

	task :uberjar do
		artifacts = compile.dependencies

		artifacts.each do |artifact|
		    Unzip.new( _(assembly_dir) => artifact ).extract
		end

		# remove dirs from assembly that should not be in uberjar
		FileUtils.rm_rf( "#{_(assembly_dir)}/example/package" )
		FileUtils.rm_rf( "#{_(assembly_dir)}/example/dir" )

		# create manifest file
		File.open( _("#{assembly_dir}/META-INF/MANIFEST.MF"), 'w') do |f| 
		    f.write("Implementation-Title: Uberjar\n")
		    f.write("Implementation-Version: #{project.version}\n") 
		    f.write("Main-Class: #{main_class}\n")
		    f.write("Created-By: Buildr\n")                 
		end

		present_dir = Dir.pwd
		Dir.chdir _(assembly_dir)
		puts "Creating #{_("target/#{project.name}-#{project.version}.jar")}" 
		`jar -cfm #{_("target/#{project.name}-#{project.version}.jar")} #{_(assembly_dir)}/META-INF/MANIFEST.MF .`
		Dir.chdir present_dir
	end
end
