(() => {
  const confirmationModal = new bootstrap.Modal("#confirmation-modal", {
    backdrop: false,
  });

  let deleteForm;

  // When user clicks modal's Yes button ...
  const yesButton = document.getElementById("modal-btn-yes");
  yesButton.addEventListener("click", () => {
    deleteForm.submit();
    yesButton.blur();
    confirmationModal.hide();
  });

  // When user clicks modal's No button ...
  const noButton = document.getElementById("modal-btn-no");
  noButton.addEventListener("click", () => {
    noButton.blur();
    confirmationModal.hide();
  });

  // Click handler for list's delete buttons.
  const confirmDelete = (event) => {
    event.preventDefault();
    // Find the form for the delete button that was clicked.
    deleteForm = document.getElementById(event.target.dataset.deleteForm);

    // Ask user to confirm.
    document.getElementById("modal-body-text").textContent =
      `${event.target.title}?`;
    confirmationModal.show();
  };

  // Attach the click handler to all of the list's delete buttons.
  const nodeList = document.querySelectorAll(".delete-button");
  for (let i = 0; i < nodeList.length; i++) {
    nodeList[i].addEventListener("click", confirmDelete);
  }
})();
