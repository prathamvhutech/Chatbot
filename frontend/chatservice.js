window.addEventListener("load", (event) => {
  let response;
  let getJsonData;
  let questionansarray;
  let faqs = document.querySelectorAll(".faq");
  (async function logJSONData() {
    response = await fetch("http://localhost:8080/api/v1/chatHpt");
    getJsonData = await response.json();
    questionansarray = Array.from(getJsonData);
    questionansarray.map((value, index) => {
      faqs[index].setAttribute("key", `${value.id}`);
      faqs[index].children[0].innerHTML = ">     " + value.question;
      faqs[index].children[1].innerHTML = value.answer;
    });
    questionansarray.map((value, index) => {
      faqs[index].children[0].addEventListener("click", () => {
        // console.log(faqs[index].children[1].style.display)
        if (faqs[index].children[1].style.display === "block") {
          console.log("none");
          faqs[index].children[1].style.display = "none";
        } else if (
          faqs[index].children[1].style.display === "" ||
          faqs[index].children[1].style.display === "none"
        ) {
          faqs[index].children[1].style.display = "block";
          console.log("block");
        }
      });
    });
  })();
  async function logJSONDataAfterQuestion() {
    let nextResponse = await fetch(
      "http://localhost:8080/api/v1/chat/getAll"
    );
    let getNextJsonData = await nextResponse.json();
    let newquestionansarray = Array.from(getNextJsonData);
    newquestionansarray.map((value, index) => {
      faqs[index].setAttribute("key", `${value.id}`);
      faqs[index].children[0].innerHTML = ">     " + value.question;
      faqs[index].children[1].innerHTML = value.answer;
    });
  }
  // Chat Functionality
  let formChat = document.querySelector("#form");
  let chatwindow = document.querySelector("#chat");
  const getanswer = async (question) => {
    let answer = "";
    try {
      const rawData = await fetch("http://localhost:8080/api/v1/chatHpt", {
        headers: {
          "Content-Type": "application/json",
        },
        method: "POST",
        body: JSON.stringify({
          question: question,
        }),
      })
        .then((data) => {
          console.log("api call repeat", data);
          logJSONDataAfterQuestion();
          return data.json();
        })
        .then((data) => {
          answer = data;
        });
    } catch (error) {
      console.log("Error", error);
    }
    let answerFormatted = answer.answer.replaceAll("\n", "<br/> ");
    console.log(answer.answer);
    const answerNode = document.createElement("p");
    answerNode.classList.add("answer");
    answerNode.innerHTML = answerFormatted;
    chatwindow.appendChild(answerNode);
  };
  function chat(event) {
    console.log(event.target.question.value);
    let message;
    // Question
    const node = document.createElement("p");
    node.classList.add("question");
    const textnode = document.createTextNode(event.target.question.value);
    node.appendChild(textnode);
    chatwindow.appendChild(node);
    // Fetch
    getanswer(event.target.question.value);
    // Answer
    event.target.question.value = "";
    event.preventDefault();
  }
  formChat.addEventListener("submit", chat);
});
