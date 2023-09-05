package dataflowsamples;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
//import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.PipelineOptions;

public class SimpleFilterJob2 {
    public interface Options extends PipelineOptions {
    }

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
        Options options = PipelineOptionsFactory.fromArgs(args).as(Options.class);
        // Create the pipeline
        Pipeline p = Pipeline.create(options);
        //Pipeline p = Pipeline.create();

        // Use GCS paths for input and output
        String inputFilePath = "gs://cloud-dataflow-java123/input.csv";
        String outputFilePath = "gs://cloud-dataflow-java123/output.csv";

        // PCollection: Represents the data read from the input file.
        PCollection<String> input = p.apply(TextIO.read().from(inputFilePath));

        // PTransform: Applying the FilterFn transformation to the input data.
        PCollection<String> output = input.apply(ParDo.of(new FilterFn()));

        // PTransform: Writing the filtered data to an output file.
        output.apply(TextIO.write().to(outputFilePath));

        p.run().waitUntilFinish();
    }
}


