import { showAlert } from "./showAlert.js";

// 검색 버튼 
document.addEventListener("DOMContentLoaded", function () {
  const searchButton = document.querySelector(".search-button");

  searchButton.addEventListener("click", function (event) {
    event.preventDefault(); // 기본 제출 동작 방지
    showAlert("app.js", ".search-button");
  });
});


