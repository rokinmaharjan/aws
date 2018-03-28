package com.aws.s3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfig {
	
	@Value("${aws.access.key}")
	private String awsAccessKey;
	
	@Value("${aws.access.secret}")
	private String awsAccessSecret;
	
	@Value("${aws.s3.base.url}")
	private String s3BaseUrl;
	
	@Value("${aws.s3.region}")
	private String s3Region;
	
	@Bean
	public AWSCredentials getAwsCredentials() {
		return new BasicAWSCredentials(awsAccessKey, awsAccessSecret);
	}

	@Bean
	public AmazonS3 getAmazonS3Client() {
		EndpointConfiguration endpointConfiguration = new EndpointConfiguration(s3BaseUrl, s3Region);
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(getAwsCredentials()))
				.withEndpointConfiguration(endpointConfiguration)
				.build();
	}
}
