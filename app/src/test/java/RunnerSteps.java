import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RunnerSteps {
    @Given("a file is saved in the input folder")
    public void a_file_is_saved_in_the_input_folder() {
        System.out.println("Given - sentence");
    }

    @When("the file is taken to processing it")
    public void the_file_is_taken_to_processing_it() {
        System.out.println("When - sentence");
    }

    @Then("an output file is created")
    public void an_output_file_is_created() {
        System.out.println("Then - sentence");
    }
}
