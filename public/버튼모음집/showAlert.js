function showAlert(path, className) {
  alert(`path: ${path} /// class: ${className} 클릭됨`);
}

export function setButtonEvent(selector, path) {
  document.addEventListener("DOMContentLoaded", function () {
    const button = document.querySelector(selector);
    if (!button) return;

    button.addEventListener("click", function (event) {
      event.preventDefault();
      const className = `.${button.className}`;
      showAlert(path, className);
    });
  });
}