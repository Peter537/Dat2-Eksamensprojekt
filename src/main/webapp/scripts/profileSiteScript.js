let popup = document.getElementById("popup");

function openPopup() {
    checkForPopup();
    popup.classList.add("open-popup");
    document.getElementById("baseBody").classList.add("after-popup");
}

function closePopup() {
    checkForPopup();
    popup.classList.remove("open-popup");
    document.getElementById("baseBody").classList.remove("after-popup");
}

function checkForPopup() {
if (popup == null) {
        popup = document.getElementById("popup");
    }
}