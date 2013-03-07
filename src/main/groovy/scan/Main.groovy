package scan

import static groovy.io.FileType.FILES
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

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
	
	protected void verifyOrder(String orderNumber){
        def http = new HTTPBuilder( 'http://localhost:9000' )

        // perform a GET request, expecting JSON response data
        http.request( GET, JSON ) {
          uri.path = "/api/order/${orderNumber}"
          headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

          // response handler for a success response code:
          response.success = { resp, json ->
            println resp.statusLine
            println json
            if(json.success == true){
                
                
                
                
                
                
                println "======================================================================="                
                println "  ___      _          ___ _             _              "
                println " |   \\ _ _(_)_ _____ / __| |___ __ _ __| |___ _ _ ___  "
                println " | |) | '_| \\ V / -_) (__| / -_) _` / _` / -_) '_(_-<  "
                println " |___/|_| |_|\\_/\\___|\\___|_\\___\\__,_\\__,_\\___|_| /__/  "
                println ""
                println json.data.customer.firstName
                println json.data.customer.lastName                
            }else{
                println "FAIL!"
            }

          }

          // handler for any failure status code:
          response.failure = { resp ->
            println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
          }
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
	      }else if(opt.o){
			//main.startScan()
			println opt.o 
			main.verifyOrder( opt.o )
			//println opt.p
		  }else if(opt.v){
			println main.version
		  }else{
			cli.usage()
		  }
		
		  
	 } 	
}