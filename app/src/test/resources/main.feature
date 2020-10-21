Feature: Reading input file and process to generate output

    Scenario: Input file is created in the input folder
        Given a file is saved in the input folder
        When the file is taken to processing it
        Then an output file is created