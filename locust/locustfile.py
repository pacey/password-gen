from locust import HttpUser, task, constant


class PasswordGenUser(HttpUser):
    wait_time = constant(1)

    @task(2)
    def long_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password?length=64")

    @task(4)
    def average_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password?length=16")

    @task(2)
    def short_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password?length=8")

    @task(2)
    def default_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password")

    @task(1)
    def validation_error(self):
        with self.client.get("http://password-gen-api:8080/api/password?length=128", catch_response=True) as response:
            if response.status_code == 400:
                response.success()

