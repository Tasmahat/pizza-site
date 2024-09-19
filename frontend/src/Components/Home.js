import React from "react";

function Home() {
    return(
        <div>
            <h1>Сначала войдите в аккаунт пожалуйста!</h1>
            <a href={'http://localhost:8181/login'}>
                <button>
                    Войти в аккаунт с помощью гугл
                </button>
            </a>
        </div>
    );
}

export default Home;