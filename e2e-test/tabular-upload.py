import requests
import tempfile

def test_upload_tabular_data():
    # Define the API endpoint URL
    url = 'http://localhost:8080/tabular/upload'  # Replace with your actual URL

    # Create a temporary CSV file with sample data
    with tempfile.NamedTemporaryFile(suffix='.csv', delete=False) as temp_file:
        temp_file.write(b'name,email\njohn,joh\nemma,emma@yahoo.com\n')
        temp_file.seek(0)

        # Define the file to upload
        files = {'file': ('example.csv', open(temp_file.name, 'rb'))}

        # Make the POST request
        response = requests.post(url, files=files)

    # Assert the response status code (200 indicates success, but it depends on your API)
    assert response.status_code == 200

# Run the test
if __name__ == '__main__':
    test_upload_tabular_data()
