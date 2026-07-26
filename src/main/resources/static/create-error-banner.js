// In element #banner, create a dismissible error banner with the specified message,
// replacing any that is already there.
const createErrorBanner = (message) => {
  const banner = document.getElementById("banner");
  const bannerMessageId = "banner-message";
  const existingDiv = document.getElementById(bannerMessageId);
  if (existingDiv != null) {
    // If an error banner is already displaying, destroy it.
    banner.removeChild(existingDiv);
  }
  // Create alert div.
  const newDiv = document.createElement("div");
  newDiv.classList.add(
    "alert",
    "alert-danger",
    "alert-dismissible",
    "fade",
    "show",
  );
  newDiv.role = "alert";
  newDiv.id = bannerMessageId;
  newDiv.innerHTML += message;
  banner.appendChild(newDiv);

  // Create close button.
  const newButton = document.createElement("button");
  newButton.type = "button";
  newButton.classList.add("btn-close");
  newButton.dataset.bsDismiss = "alert";
  newButton.ariaLabel = "Close";
  newDiv.appendChild(newButton);
};
