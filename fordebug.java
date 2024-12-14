    private Process commandProcess; 
    private void startCommand(String command, TextArea terminalOutput) {
        new Thread(() -> {
            try {    
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
                    processBuilder.redirectErrorStream(true);
                    Process commandProcess = processBuilder.start();
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(commandProcess.getInputStream())); 
                    String line;
                    
                    while ((line = reader.readLine()) != null) {
                        String finalLine = line;
                        Platform.runLater(() -> terminalOutput.appendText(finalLine + "\n"));
                    }
                    
            } catch (Exception e) {
                Platform.runLater(() -> terminalOutput.appendText(" Error: " + e.getMessage() + "\n"));
            
            }
        }).start();
    
    
    }
    
    private void stopCommand(TextArea terminalOutput) {

        if (commandProcess != null) {
            commandProcess.destroy();
            Platform.runLater(() -> terminalOutput.appendText("command Stopped \n"));

        } else {
            Platform.runLater(() -> terminalOutput.appendText("No command to stop \n"));
        }    
  
    }

    public static void main(String[] args) {
        launch(args);
    }
}
