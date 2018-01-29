package org.nu.msc.service;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerDropwizard;

public class Appinitializer extends Application<Configuration> {

//	private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();
/*	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
		swaggerDropwizard.onInitialize(bootstrap);
	}*/
	@Override
	public void run(Configuration arg0, Environment env) throws Exception {
		env.jersey().register(new ConfigService());
		
//		swaggerDropwizard.onRun(arg0, env);

	}

	public static void main(String[] args) {
		try {
			new Appinitializer().run(args);
		} catch (Exception e) {
			System.out.println("Unable to start the server " + e.getMessage());
		}
	}

}
