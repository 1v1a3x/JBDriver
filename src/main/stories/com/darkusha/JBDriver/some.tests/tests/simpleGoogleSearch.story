The simple test

Narrative:
    In order to test a Test Web UI availability
    As a Test User
    I should be able to obtain search results from Test Web UI application

Meta:
    @example

Scenario: Positive - The google user should be able to perform a simple googleSearch
    Given User is on Google page
    When User try to perform the search by the 'madonna' criteria
    Then User 'IS' able to see several search results
    And the page title is not 'bla bla'