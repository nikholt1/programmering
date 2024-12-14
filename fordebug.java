    private void captureTerminalOutput(long gnomeTerminalPid, TextArea terminalOutput) {
    
        try {
            String pidStr = String.valueOf(gnomeTerminalPid);
            
            Process findTtyProcess = new ProcessBuilder("sudo", "ps", "--ppid", pidStr, "-o", "tty=", "|", "grep", "-v", "'?'", "|", "head", "-n", "1").start();
            Platform.runLater(() -> terminalOutput.appendText("sudo", "ps", "--ppid", pidStr, "-o", "tty=", "|", "grep", "-v", "'?'", "|", "head", "-n", "1"));
            System.out.println(findTtyProcess);
            BufferedReader ttyReader = new BufferedReader(new InputStreamReader(findTtyProcess.getInputStream()));
            String tty = ttyReader.readLine();
            
            if (tty == null || tty.trim().isEmpty()) {
                Platform.runLater(() -> terminalOutput.appendText("ERROR coud not find valid tty for PID " + gnomeTerminalPid + "\n"));
                Platform.runLater(() -> terminalOutput.appendText("TTY trimmed for debug: " + tty.trim()));
            
            }
            
            String ptyPath = "/dev/" + tty.trim();
            String command = "cat " + ptyPath;
            
            
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                Platform.runLater(() -> terminalOutput.appendText(finalLine + "\n"));

            }
        
        
        } catch (Exception e) {
            Platform.runLater(() -> terminalOutput.appendText("Error for debug: " + e.getMessage() + "\n"));

        }
    
    
    }
