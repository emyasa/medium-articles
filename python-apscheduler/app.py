from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.triggers.cron import CronTrigger
from pytz import utc
from datetime import datetime
import json

scheduled_jobs_map = {}

def schedule_jobs(scheduler):
    jobs = retrieve_jobs_to_schedule()
    for job in jobs: 
        add_job_if_applicable(job, scheduler)
        update_job_if_applicable(job, scheduler)

    print("refreshed scheduled jobs")

def retrieve_jobs_to_schedule():
    with open('jobs.json') as f:
        d = json.load(f)

    return d    

def add_job_if_applicable(job, scheduler): 
    job_id = str(job['id'])
    if (job_id not in scheduled_jobs_map):
        scheduled_jobs_map[job_id] = job
        scheduler.add_job(lambda: execute_job(job), CronTrigger.from_crontab(job['cron_expression'], timezone='UTC'), id=job_id)
        
        print("added job with id: " + str(job_id))

def update_job_if_applicable(job, scheduler):
    job_id = str(job['id'])
    if (job_id not in scheduled_jobs_map):
        return

    last_version = scheduled_jobs_map[job_id]['version']
    current_version = job['version']
    if (current_version != last_version):
        scheduled_jobs_map[job_id]['version'] = current_version
        scheduler.remove_job(job_id)
        scheduler.add_job(lambda: execute_job(job), CronTrigger.from_crontab(job['cron_expression'], timezone='UTC'), id=job_id)
        print("updated job with id: " + str(job_id))

def execute_job(job):
    print("executing job with id: " + str(job['id']))
    print(datetime.utcnow())

scheduler = BackgroundScheduler(timezone=utc)
scheduler.add_job(lambda: schedule_jobs(scheduler), 'interval', seconds=5, next_run_time=datetime.utcnow(), id='scheduler-job-id')
scheduler.start()

input()