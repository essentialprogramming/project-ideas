
     docReady(async function () {

        document.getElementById("output").innerHTML = await json2Table(await getQuizList());
    });

    async function json2Table(json) {

        let headerRow = `<tr>
                      <th>Quiz id</th>
                      <th>Score</th>
                     </tr>`

        let rows = json
            .map(evaluation => {
                return `
              <tr>

                <td>${evaluation.quiz}</td>
                <td>${evaluation.score}</td>

              </tr>
              `
            });

        const table = `
        	<table>
		      <thead>
		    	<tr>${headerRow}</tr>
		      <thead>
		      <tbody>
		    	${rows.join('')}
		      <tbody>
	        <table>
        `;

        return table;
    }

    async function getQuizList(){

        let user = sessionStorage.username;
        let url = '/api/quiz/score?username='+user;
        let response = await fetch(url)
            .then(res => res.json())
            .then(json => {
                return json;

            })
            .catch(function(error) {
                console.log(error);
            });

        console.log(response)
        return response;
    }
