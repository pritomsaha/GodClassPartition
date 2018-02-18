import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final int minLoc = 1500;

    public List<File> getJavaFiles(String projectPath){
        File directory = new File(projectPath);
        List<File> javaFiles = new ArrayList<>();
        traverseDirectory(directory, javaFiles);
        return javaFiles;
    }

    private void traverseDirectory(File fileEntry, List<File> javaFiles){
        if(fileEntry.isFile()){
            if(fileEntry.getName().endsWith(".java")) {
                if(this.getLoc(fileEntry)>=minLoc)
                    javaFiles.add(fileEntry);
            }
            return;
        }
        for (File fEntry: fileEntry.listFiles())
            traverseDirectory(fEntry, javaFiles);
    }

    private int getLoc(File file) {
        BufferedReader bf;
        int loc = 0;
        try {
            bf = new BufferedReader(new FileReader(file));
            String line;
            boolean isComment = false;
            while((line = bf.readLine())!=null){
                line = line.replaceAll("\\n|\\t|\\s|}|\\{", "");

                if(line.endsWith("*/")){
                    isComment = false;
                    continue;
                }

                if(isComment)continue;

                if(line.startsWith("\\*")){
                    isComment = true;
                    continue;
                }

                if((!line.equals("")) && (!line.startsWith("//"))) {
                    loc++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loc;
    }
}
