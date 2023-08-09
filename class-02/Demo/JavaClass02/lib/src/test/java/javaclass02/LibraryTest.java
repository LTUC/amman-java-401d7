/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package javaclass02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LibraryTest {

    //we use the TDD
    @Test
    public void testAddition() {
        //sut ===> mean system under test (almost every java developer use this convention name)
        /*Arrange:  In the Arrange phase, we set up the necessary things for the test case.
        This includes initializing objects, preparing any data required for the test and so on.so we should think
        about the Arrange phase as a scenario because we really build a scenario that the test will operate on.
        */
        Library sut = new Library();

         /*Act:In the Act phase, we perform the action or behavior that you want to test.
          like calling a function. the goal of this phase is to perform action based on the arrange phase
          condition or the things that we set up to test it in the Arrange phase.
          */
        int result = sut.add(5, 5);

         /*Assert:In the Assert phase, you verify the expected outcome or result of the test.
          in this phase we create one or more than one assert and we check or verify if the actual
          result match the expected one so if the assert rerun true then the test case pass
          and if the assert return false the tes case fail.
          */
        Assertions.assertEquals(10, result);
    }
}