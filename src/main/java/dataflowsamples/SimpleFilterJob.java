package dataflowsamples;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

public class SimpleFilterJob {
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
        PipelineOptionsFactory.register(JobOptions.class);
        JobOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);
        Pipeline p = Pipeline.create(options);

        // PCollection: Represents the data read from the input file.
        PCollection<String> input = p.apply(TextIO.read().from(options.getInputFile()));

        // PTransform: Applying the FilterFn transformation to the input data.
        PCollection<String> output = input.apply(ParDo.of(new SimpleFilterJob.FilterFn()));

        // PTransform: Writing the filtered data to an output file.
        output.apply(TextIO.write().to(options.getOutputFile()));

        p.run().waitUntilFinish();
    }
}


