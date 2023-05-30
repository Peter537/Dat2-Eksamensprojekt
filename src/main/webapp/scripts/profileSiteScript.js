let popup = document.getElementById("popup"); // Get the popup

function openPopup() {
    checkForPopup(); // Check if popup is null (if it is, get it)
    popup.classList.add("open-popup"); // Add the class to open the popup
    document.getElementById("baseBody").classList.add("after-popup"); // Add the class to blur the background
}

function closePopup() {
    checkForPopup(); // Check if popup is null (if it is, get it)
    popup.classList.remove("open-popup"); // Remove the class to open the popup
    document.getElementById("baseBody").classList.remove("after-popup"); // Remove the class to blur the background
}

function checkForPopup() {
    if (popup == null) {
        popup = document.getElementById("popup");
    }
}