package scan

import static groovy.io.FileType.FILES

/*
	def dir = new File('/home/groovy/projects/test/src')
	dir.traverse(
        type:FileType.FILES,
        nameFilter:~/.*\.groovy/,
        maxDepth:0
    ) {
        println it
    };

	dir.traverse(
	type:FileType.FILES,
	nameFilter:~/.*\.jar|.*\.java|.*\.xml/
	) {
	println it
	};

*/


public class Main{

	String exp = '.*\\.jar|.*\\.java|.*\\.xml|.*\\.groovy'
	def pattern = ~exp
	
	protected void startScan(){
		new File('.').eachFileRecurse(FILES) {
			if( pattern.matcher(it.name).matches() ){
				println it
			}
		    //if(it.name.endsWith('.groovy')) {
		    //    println it
		    //}
		}	
	}
	protected String getVersion(){
		return "0.1-alpha"
	}
	
	protected boolean assignOrderNumber(){
		
	}

	public static void main(String[] args){
		
		println args
		
		
		
		Main main = new Main()
	
		def cli = new CliBuilder()
	    cli.with{
            h( 'Help - Usage Information')
			o( 'Order number example: -o 999006754', args:1,argName:'Order Number')
			p( 'Path to drive example: -p /Volumes/Untitled1',args:1,argName:'Path')
			v( 'Version number')}
			
	    def opt = cli.parse(args)
		
	    println opt.o
		println opt.p

	      if (opt.h){
	 		cli.usage()
	      }else if(opt.o && opt.p){
			main.startScan()
			println opt.o 
			println opt.p
		  }else if(opt.v){
			println main.version
		  }else{
			cli.usage()
		  }
		
		  
	 } 	
}