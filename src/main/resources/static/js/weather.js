(function () {

    const API_KEY = '35ef0d68d6a20d1dd3105003e00b6e77';

    const morningTempSection = document.querySelector('.morningTemp');
    const morningDescSection = document.querySelector('.morningDesc');
    const afternoonTempSection = document.querySelector('.afternoonTemp');
    const afternoonDescSection = document.querySelector('.afternoonDesc');
    const iconSection = document.querySelector('.weatherIcon');

    window.getWeather = (lat, lon, date) => {
        fetch(
            `https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
        ).then((response) => {
            return response.json();
        }).then((json) => {
            console.log(json);

            // 받아온 예보 중에서 사용자가 선택한 날짜에 해당하는 예보를 모두 찾습니다.
            const forecasts = json.list.filter((item) => {
                const forecastDate = new Date(item.dt_txt).toISOString().split('T')[0];
                return forecastDate === date;
            });

            // 찾은 예보 중에서 오전 예보를 찾습니다.
            const morningForecast = forecasts.find((forecast) => {
                const hour = new Date(forecast.dt_txt).getHours();
                return hour >= 6 && hour < 12;
            });

            // 찾은 예보 중에서 오후 예보를 찾습니다.
            const afternoonForecast = forecasts.find((forecast) => {
                const hour = new Date(forecast.dt_txt).getHours();
                return hour >= 12 && hour < 18;
            });

            // 오전 예보를 화면에 표시합니다.
            if (morningForecast) {
                const temperature = morningForecast.main.temp;
                const description = morningForecast.weather[0].description;

                morningTempSection.innerText = temperature;
                morningDescSection.innerText = description;
            }

            // 오후 예보를 화면에 표시합니다.
            if (afternoonForecast) {
                const temperature = afternoonForecast.main.temp;
                const description = afternoonForecast.weather[0].description;

                afternoonTempSection.innerText = temperature;
                afternoonDescSection.innerText = description;
            }

            // 오전 예보와 오후 예보 둘 다 받아올 수 없으면 알림을 띄웁니다.
            if (!morningForecast && !afternoonForecast) {
                alert('해당 날짜의 날씨 정보를 찾을 수 없습니다.');
            }

        }).catch((error) => {
            alert(error);
        });
    };

})();