from faker import Faker
import csv

fake = Faker()
Faker.seed(123)

num_records = 10000

with open("fake_addresses.csv", mode="w", newline="", encoding="utf-8") as file:
    writer = csv.writer(file)
    writer.writerow(["Address"])  # Header for a single column

    for _ in range(num_records):
        street = fake.street_address()
        city = fake.city()
        state = fake.state_abbr()
        zip_code = fake.zipcode()

        full_address = f"{street}, {city}, {state} {zip_code}"
        writer.writerow([full_address])
