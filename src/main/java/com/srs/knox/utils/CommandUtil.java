package com.srs.knox.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.srs.knox.models.UserCreationStatus;

@Component
public class CommandUtil {

	public UserCreationStatus createOwUser(String username) {
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		
		UserCreationStatus status = new UserCreationStatus();
        // Windows
        processBuilder.command("cmd.exe", "/c", "kubectl -n openwhisk  -ti exec owdev-wskadmin -- wskadmin user create " + username);

        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
           if((line = reader.readLine()) != null) {
               status.setMessage(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
            status.setExitCode(exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return status;
	}
}
