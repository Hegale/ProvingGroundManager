(function () {

    const API_KEY = '35ef0d68d6a20d1dd3105003e00b6e77';

    const tempSection = document.querySelector('.temperature');
    const placeSection = document.querySelector('.place');
    const descSection = document.querySelector('.description');
    const getWeather = (lat, lon) => {
        fetch(
            `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
        ).then((response) => {
            return response.json();
        }).then((json) => {
            console.log(json);
            /*
            const temperature = json.main.temp;
            const place = json.name;
            const description = json.weather[0].description;

            tempSection.innerText = temperature;
            placeSection.innerText = place;
            descSection.innerText = description;

             */
        }).catch((error) => {
            alert(error);
        });
    }
})();