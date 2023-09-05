package dataflowsamples;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.File;

public class SimpleFilterJobLocal {

    // This is a PTransform that defines the logic to filter data based on age.
    static class FilterFn extends DoFn<String, String> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            // Skip the header line
            if (!c.element().equals("Name,Age")) {
                String[] elements = c.element().split(",");
                int age = Integer.parseInt(elements[1]);
                if (age >= 29) {
                    c.output(c.element());
                }
            }

        }
    }

    public static void main(String[] args) {
        Pipeline p = Pipeline.create();

        // Read the input.csv file from the resources directory
        String currentDirectory = System.getProperty("user.dir");
        String inputFilePath = currentDirectory+ File.separator+"src"+File.separator+"main"
                +File.separator+"resources"+File.separator+"input" +File.separator+ "input.csv";
        String outputFilePath = currentDirectory+ File.separator+"src"+File.separator+"main"
                +File.separator+"resources"+File.separator+"output" +File.separator+ "output.csv";

        // PCollection: Represents the data read from the input file.
        PCollection<String> input = p.apply(TextIO.read().from(inputFilePath));

        // PTransform: Applying the FilterFn transformation to the input data.
        PCollection<String> output = input.apply(ParDo.of(new FilterFn()));

        // PTransform: Writing the filtered data to an output file.
        output.apply(TextIO.write().to(outputFilePath));

        p.run().waitUntilFinish();
    }
}


