// Use Bootstrap CSS classes to mark validity for required inputs.

(() => {
  const validateRequiredInputs = (event) => {
    const requiredInputs = document.querySelectorAll(":required");
    let error = false;
    for (const input of requiredInputs) {
      if (input.value.trim().length == 0) {
        error = true;
        input.classList.add("is-invalid");
      } else {
        input.classList.remove("is-invalid");
      }
    }
    if (error) event.preventDefault(); // no submit
  };

  const submitButton = document.querySelector("#submit");
  submitButton.addEventListener("click", validateRequiredInputs);
})();
