import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReaderx {

    // Read the entire file content to a string
    public static String readToContinuousString(String filePath, int startLine, int endLine) {
        StringBuilder stringBuilder = new StringBuilder();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Append the line content to the StringBuilder
                stringBuilder.append(lineContent);
                stringBuilder.append(System.lineSeparator());
                
                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        }
        
        return stringBuilder.toString();
    }

    // Read one line of the file to different data types
    public static String[] readLineToWordString(String filePath, int line) {
        List<String> wordList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {           
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < line; lineNumber++) {
                bufferedReader.readLine();
            }
            
            String lineContent = bufferedReader.readLine();
            // Split the line content by whitespace
            String[] words = lineContent.split("\\s+");
            
            // Add each word to the list
            for (String word : words) {
                if (word.matches("[a-zA-ZÄÖÜäöüß]+")) {
                    wordList.add(word);
                }
            }
            
            lineContent = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        }

        return wordList.toArray(new String[0]);
    }

    public static int readLineToInt(String filePath, int line) {
        int number = 0;
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < line; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the line
            String lineContent = bufferedReader.readLine();
            // Convert the line content to an integer
            number = Integer.parseInt(lineContent);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
        }
        
        return number;
    }

    public static float readLineToFloat(String filePath, int line) {
        float number = 0;
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < line; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the line
            String lineContent = bufferedReader.readLine();
            // Convert the line content to a float
            number = Float.parseFloat(lineContent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to float failed.");
        }

        return number;
    }

    public static boolean readLineToBool(String filePath, int line) {
        boolean bool = false;
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < line; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the line
            String lineContent = bufferedReader.readLine();
            // Convert the line content to a boolean
            bool = Boolean.parseBoolean(lineContent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to boolean failed.");
        }
    
        return bool;
    }

    // Read a Part of the File to different types of Arrays
    public static char[] readToCharArray(String filePath, int startLine, int endLine) {
        List<Character> charList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Store each character as char in the list
                for (char character : lineContent.toCharArray()) {
                    charList.add(character);
                }

                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to char failed.");
        }
        
        // Convert the list to a char[]
        char[] charArray = new char[charList.size()];
        for (int i = 0; i < charList.size(); i++) {
            charArray[i] = charList.get(i);
        }
        
        return charArray;
    }

    public static String[] readToStringArray(String filePath, int startLine, int endLine) {
        List<String> stringList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Add the line content to the list
                stringList.add(lineContent);
                
                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        }
        
        return stringList.toArray(new String[0]);
    }

    public static int[] readToIntArray(String filePath, int startLine, int endLine) {
        List<Integer> intList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Convert the line content to an integer
                int number = Integer.parseInt(lineContent);
                // Add the integer to the list
                intList.add(number);
                
                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to integer failed.");
        }
        
        // Convert the list to an int[]
        int[] intArray = new int[intList.size()];
        for (int i = 0; i < intList.size(); i++) {
            intArray[i] = intList.get(i);
        }
        
        return intArray;
    }

    public static float[] readToFloatArray(String filePath, int startLine, int endLine) {
        List<Float> floatList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Convert the line content to a float
                float number = Float.parseFloat(lineContent);
                // Add the float to the list
                floatList.add(number);
                
                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to float failed.");
        }
        
        // Convert the list to a float[]
        float[] floatArray = new float[floatList.size()];
        for (int i = 0; i < floatList.size(); i++) {
            floatArray[i] = floatList.get(i);
        }
        
        return floatArray;
    }

    public static boolean[] readToBoolArray(String filePath, int startLine, int endLine) {
        List<Boolean> boolList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Convert the line content to a boolean
                boolean bool = Boolean.parseBoolean(lineContent);
                // Add the boolean to the list
                boolList.add(bool);
                
                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to boolean failed.");
        }
        
        // Convert the list to a boolean[]
        boolean[] boolArray = new boolean[boolList.size()];
        for (int i = 0; i < boolList.size(); i++) {
            boolArray[i] = boolList.get(i);
        }
        
        return boolArray;
    }

    // Read a Part of the File to 2D Arrays
    public static char[][] readToChar2DArray(String filePath, int startLine, int endLine) {
        List<char[]> charList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Convert the line content to a char array and add to the list
                charList.add(lineContent.toCharArray());

                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        }
        
        return charList.toArray(new char[0][]);
    }

    public static String[][] readToString2DArray(String filePath, int startLine, int endLine) {
        List<String[]> stringList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Split the line content by whitespace and add to the list
                stringList.add(lineContent.split("\\s+"));

                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        }
        
        return stringList.toArray(new String[0][]);
    }

    public static int[][] readToInt2DArray(String filePath, int startLine, int endLine) {
        List<int[]> intList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Split the line content by whitespace, convert to integers and add to the list
                String[] parts = lineContent.split("\\s+");
                int[] intArray = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    intArray[i] = Integer.parseInt(parts[i]);
                }
                intList.add(intArray);

                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to integer failed.");
        }
        
        return intList.toArray(new int[0][]);
    }

    public static float[][] readToFloat2DArray(String filePath, int startLine, int endLine) {
        List<float[]> floatList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            
            // Skip the first lines until the desired start line
            for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
                bufferedReader.readLine();
            }
            
            // Read the file line by line
            String lineContent;
            while ((lineContent = bufferedReader.readLine()) != null) {
                // Split the line content by whitespace, convert to floats and add to the list
                String[] parts = lineContent.split("\\s+");
                float[] floatArray = new float[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    floatArray[i] = Float.parseFloat(parts[i]);
                }
                floatList.add(floatArray);

                // Stop reading if the end line is reached
                if (endLine != -1 && endLine == startLine) {
                    break;
                }
                startLine++;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File does not exist.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Reading failed.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Conversion to float failed.");
        }
        
        return floatList.toArray(new float[0][]);
    }

public static char[] readToContinuousCharArray(String filePath, int startLine, int endLine) {
    StringBuilder stringBuilder = new StringBuilder();
    
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
        
        // Skip the first lines until the desired start line
        for (int lineNumber = 0; lineNumber < startLine; lineNumber++) {
            bufferedReader.readLine();
        }
        
        // Read the file line by line
        String lineContent;
        while ((lineContent = bufferedReader.readLine()) != null) {
            // Append the line content to the StringBuilder
            stringBuilder.append(lineContent);
            
            // Stop reading if the end line is reached
            if (endLine != -1 && endLine == startLine) {
                break;
            }
            startLine++;
        }
        
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.err.println("File does not exist.");
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Reading failed.");
    }
    
    // Convert the StringBuilder to a char array
    return stringBuilder.toString().toCharArray();
}
}
