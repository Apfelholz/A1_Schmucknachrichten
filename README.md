# A1_Schmucknachrichten

## Project Description
This project is part of the BWINF 2024/2025, Round 2. The task A1_Schmucknachrichten focuses on message compression.

## Directory Structure
```
A1_Schmucknachrichten/
│
├── src/                # Source code of the project
├── data/               # Datasets and input files
└── README.md           # This document
```

## How to Run
Ensure you have Java installed on your system. Run the main program with:
```bash
java -jar <program-file-path> <input-file-path> <method>
```

Possible methods are `Huffman` and `SIG`, respectively, for the Huffman Algorithm and the Günther-Rothe-Algorithm.

## Input Format

The input is a `.txt` file that holds the message with the pearl types. The structure is as follows:

1. **First Line**:
    - Specifies the number of different pearl types.
2. **Pearl Types**:
    - The circumference of the different pearl types in mm.
3. **The Message**:
    - The message to be encoded. All Unicode symbols are supported.
4. **Length Request**:
    - An optional line specifying a length the encoded message should have in the best case. (If nothing is specified, the algorithm tries to make it as short as possible.)

## Output Format

The program outputs to the console and to a file under `output\\output_<method>_<input-file>.txt`.

> ⚠️ **Warning:**  
> The display of the Unicode symbols isn't perfect in the console. The output to the file is much better (perfect🤞). If there is a problem with the rendering in the plain text output, please refer to the output in Unicode codes at the end of the file.

It is also possible to get a visualization of the code tree from the [code_visualization](https://github.com/Apfelholz/code_visualization).

## References

- Maze visualization (helper script): [code_visualization](https://github.com/Apfelholz/code_visualization)
- Task description: [BWINF 2024 Round 2 Task PDF](https://bwinf.de/fileadmin/wettbewerbe/bundeswettbewerb/43/2_runde/Aufgaben432.pdf)

## Authors
- Jonathan Salomo @apfelholz
