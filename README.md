QKART e-commerce website :
Project Report: QKart Automation:

Objective:
The objective of this automation project is to ensure that the QKart e-commerce platform functions as expected under different scenarios. The key functionalities tested include user registration, login, product search, cart management, and checkout processes.
Technologies Used:

    Selenium WebDriver: For automating web interactions on the QKart website.
    TestNG: A testing framework used to manage and execute tests.
    Java: Programming language used for writing test scripts.
    Zalenium: For running tests across multiple browsers remotely.
    Maven: Dependency management for the project.

Test Scenarios:

The following test cases have been implemented for the QKart platform:
Test Case 01: User Registration

  Objective: Verify a new user can successfully register.
    Flow:
        Navigate to the registration page.
        Register a new user.
        Save the generated username for login.
        Log in with the registered user credentials.
        Verify login is successful.
        Log out the user.

Test Case 02: Prevent Re-Registration

    Objective: Verify that a registered user cannot register again with the same credentials.
    Flow:
        Attempt to register a user with existing credentials.
        Ensure the system prevents re-registration.

Test Case 03: Product Search

    Objective: Verify the functionality of the search text box.
    Flow:
        Search for a product (e.g., "YONEX").
        Ensure search results are relevant.
        Search for a non-existing product and verify no results.

Test Case 04: Size Chart Validation

    Objective: Verify the existence of size charts for certain products and validate their contents.
    Flow:
        Search for a product.
        Verify that the product has a size chart.
        Validate the size chart's contents against expected data.

Test Case 05: Add Products to Cart and Checkout

    Objective: Verify that a new user can add multiple products to the cart and complete the checkout process.
    Flow:
        Register a new user.
        Log in and add products to the cart.
        Proceed to checkout, enter address details, and place the order.
        Verify that the user is redirected to the "Thank You" page after placing the order.

Test Case 06: Edit Cart Contents

    Objective: Verify that the contents of the cart can be edited.
    Flow:
        Add products to the cart.
        Change the quantity of products and remove items.
        Proceed to checkout and complete the order.

Test Case 07: Cart Persistence Across Sessions

    Objective: Verify that the cart contents are saved across user sessions.
    Flow:
        Add products to the cart and log out.
        Log in again and verify that the cart retains the added products.

Test Case 08: Insufficient Balance Error

    Objective: Verify that an error is thrown when the wallet balance is insufficient to complete the order.
    Flow:
        Add products to the cart.
        Attempt to checkout with an insufficient balance.
        Ensure the correct error message is displayed.

Test Execution Setup:

    Before Suite:
        Create the WebDriver instance using Zalenium, which launches the browser remotely.
        Set up the environment for test execution (browser configuration).

    Test Execution:
        TestNG runs the tests sequentially, verifying the key functionalities of QKart (registration, login, cart, and checkout).
        Test data is provided via a data provider (DProvider), ensuring that tests can be executed with different inputs.

    After Suite:
        Cleanup actions (e.g., logging out the user) are performed after all tests are executed.

TestNG Annotations:

    @BeforeSuite: Initializes the WebDriver before the test suite begins.
    @Test: Defines individual test cases and their respective functionality.
    @AfterSuite: Performs cleanup after all tests are executed.

Challenges:

    Cross-Browser Testing: Using Zalenium for cross-browser testing was essential for ensuring compatibility across different browsers and devices.
    Data Dependency: Ensuring test data, such as user credentials, product names, and other inputs, were consistently available during test execution.

Test Case 09: Verify Cart Content Availability Across Tabs

This test case checks that products added to the cart are preserved when a new browser tab is opened.

Steps Involved:

    User Registration & Login: A new user is registered and logged in with the username "testUser".
    Product Search & Addition: The user searches for a specific product ("YONEX Smash Badminton Racquet") and adds it to the cart.
    New Tab Navigation: The user clicks on the "Privacy policy" link, opening a new tab.
    Verification: The test ensures that the cart contents (the product added) are still available when the user returns to the original tab.

Key Assertion:

    The product added to the cart should still be visible after navigating to a new tab and then back.

Test Case 10: Verify Functionality of Privacy Policy and About Us Links

This test case ensures that the privacy policy and about us links function correctly by opening new tabs.

Steps Involved:

    User Registration & Login: The user registers and logs in successfully.
    Link Navigation: The user clicks on the "Privacy policy" link and verifies that it opens in a new tab with the correct content.
    Second Link Navigation: The user then clicks on the "Terms of Service" link, again verifying that it opens correctly in a new tab.
    Tab Verification: It ensures that the content of the new tabs matches the expected titles and does not affect the main window URL.

Key Assertion:

    The new tabs should contain the correct content (Privacy Policy, Terms of Service) without affecting the main page URL.

Test Case 11: Verify Contact Us Dialog Functionality

The goal of this test case is to ensure that the "Contact Us" form works correctly and submits the necessary details.

Steps Involved:

    Navigate to Contact Us: The user clicks on the "Contact Us" link on the homepage.
    Form Filling: The user enters the contact information, including name, email, and a query.
    Form Submission: The user submits the form by clicking the "Contact Us" button.

Key Assertion:

    Ensure that the form submits successfully and the user’s query is recorded.

Test Case 12: Ensure Advertisements on the QKART Page are Clickable

This test case verifies that the advertisements on the page are functioning correctly by checking if they are clickable.

Steps Involved:

    User Registration & Login: The user is registered and logged in.
    Product Search & Cart Operations: The user adds a product to the cart and proceeds to checkout.
    Ad Verification: The test ensures that three advertisement frames are visible on the page.
    Ad Interaction: Clicking on the "Buy Now" button in the advertisements should redirect the user, and the current page URL should be different from the original.

Key Assertion:

    Ensure that clicking on an advertisement opens a new link without affecting the current page.

Conclusion:

These test cases cover a broad range of functionalities on the e-commerce website, focusing on the user registration, login process, product search, cart management, checkout process, and advertisement handling. By running these tests, we can ensure that core features work as expected, providing a seamless user experience across different interactions and scenarios.
