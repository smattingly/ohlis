// Validate form input: some fields are required.

(() => {
  const validateNewLegislationInput = (event) => {
    const requiredInputIds = ["Title", "Text"];
    for (const inputId of requiredInputIds) {
      const input = document.getElementById(inputId.toLowerCase());
      if (input.value.trim().length == 0) {
        event.preventDefault();
        // TODO improve this with CSS framework.
        alert(`A value for ${inputId} is required.`);
        break;
      }
    }
  };

  const submitButton = document.querySelector("#submit");
  submitButton.addEventListener("click", validateNewLegislationInput);
})();
