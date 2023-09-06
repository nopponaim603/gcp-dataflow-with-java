package dataflowsamples;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface JobOptions extends PipelineOptions {
    @Description("Input File to process.")
    String getInputFile();
    void setInputFile(String inputFile);

    @Description("Output file prefix")
    String getOutputFile();
    void setOutputFile(String outputFile);
}
