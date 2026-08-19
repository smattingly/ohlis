// Use Bootstrap CSS classes to mark validity for required inputs.

(() => {
  if (document.cookie.indexOf("toggle_client_validation_off=") >= 0) {
    console.info("Client side validation is disabled by feature flag.");
    return;
  }

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
