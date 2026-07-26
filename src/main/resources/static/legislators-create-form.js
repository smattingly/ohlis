// Validate form input: all fields are required.

(() => {
  const validateNewLegislatorInput = (event) => {
    const inputFields = document.querySelectorAll("input");
    for (const input of inputFields) {
      if (input.value.trim().length == 0) {
        event.preventDefault();
        createErrorBanner("All data values are required.");
        break;
      }
    }
  };

  const submitButton = document.querySelector("#submit");
  submitButton.addEventListener("click", validateNewLegislatorInput);
})();
