from locust import HttpUser, task, between


class PasswordGenUser(HttpUser):
    wait_time = between(.25, 1)

    @task(1)
    def long_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password?length=64")

    @task(3)
    def average_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password?length=16")

    @task(1)
    def short_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password?length=8")

    @task(1)
    def default_passwords(self):
        self.client.get("http://password-gen-api:8080/api/password")
